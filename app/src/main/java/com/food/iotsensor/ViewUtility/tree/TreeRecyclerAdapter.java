package com.food.iotsensor.ViewUtility.tree;

import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 树级结构recycleradapter.
 * item之间有子父级关系,
 */

public class TreeRecyclerAdapter extends BaseRecyclerAdapter<TreeItem> {

    private TreeRecyclerType type;

    private ItemManager<TreeItem> mItemManager;
    //第一次加载，展示第一条内容详细
    private boolean isFirstLoadData  = true;
    private boolean isSecondShow = true;

    @Override
    public void onBindViewHolderClick(final ViewHolder holder) {
        if (!holder.itemView.hasOnClickListeners()) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int layoutPosition = holder.getLayoutPosition();
                    //检查item的position,这个item是否可以点击
                    if (getCheckItem().checkPosition(layoutPosition)) {
                        //获得处理后的position
                        int itemPosition = getCheckItem().getAfterCheckingPosition(layoutPosition);
                        //拿到BaseItem
                        TreeItem item = getDatas().get(itemPosition);
                        //展开,折叠和item点击不应该同时响应事件.
                        //必须是TreeItemGroup才能展开折叠,并且type不能为 TreeRecyclerType.SHOW_ALL
                        if (type != TreeRecyclerType.SHOW_ALL && item instanceof TreeItemGroup) {
                            //展开,折叠
                            expandOrCollapse(((TreeItemGroup) item));
                        } else {
                            TreeItemGroup itemParentItem = item.getParentItem();
                            //判断上一级是否需要拦截这次事件，只处理当前item的上级，不关心上上级如何处理.
                            if (itemParentItem != null && itemParentItem.onInterceptClick(item)) {
                                return;
                            }
                            if (mOnItemClickListener != null) {
                                mOnItemClickListener.onItemClick(holder, itemPosition);
                            } else {
                                //拿到对应item,回调.
                                getDatas().get(itemPosition).onClick();
                            }
                        }
                    }
                }
            });
        }
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //获得holder的position
                int layoutPosition = holder.getLayoutPosition();
                //检查position是否可以点击
                if (getCheckItem().checkPosition(layoutPosition)) {
                    //检查并得到真实的position
                    int itemPosition = getCheckItem().getAfterCheckingPosition(layoutPosition);
                    if (mOnItemLongClickListener != null) {
                        return mOnItemLongClickListener.onItemLongClick(holder, itemPosition);
                    }
                }
                return false;
            }
        });
    }

    @Override
    public List<TreeItem> getDatas() {
        return super.getDatas();
    }

    @Override
    public void setDatas(List<TreeItem> items) {
        if (null == items) {
            return;
        }
        getDatas().clear();
        assembleItems(items);
    }

    public void setDatas(TreeItemGroup treeItemGroup) {
        if (null == treeItemGroup) {
            return;
        }
        getDatas().clear();
        ArrayList<TreeItem> childItemsWithType = ItemHelperFactory.getChildItemsWithType(treeItemGroup, type);
        childItemsWithType.add(0, treeItemGroup);
        assembleItems(childItemsWithType);
    }

    /**
     * 对初始的一级items进行遍历,将每个item的childs拿出来,进行組合。
     *
     * @param items
     */
    private void assembleItems(List<TreeItem> items) {
        if (type != null) {
            List<TreeItem> datas = getDatas();
            datas.addAll(ItemHelperFactory.getChildItemsWithType(items, type));
        } else {
            super.setDatas(items);
        }
    }

    public ItemManager<TreeItem> getItemManager() {
        if (mItemManager == null) {
            mItemManager = new TreeItemManageImpl(this);
        }
        return mItemManager;
    }

    public void setItemManager(ItemManager<TreeItem> itemManage) {
        this.mItemManager = itemManage;
    }

    @Override
    public int getLayoutId(int position) {
        return getDatas().get(position).getLayoutId();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TreeItem t = getDatas().get(position);
        checkItemManage(t);
        t.onBindViewHolder(holder);
    }

    private void checkItemManage(TreeItem item) {
        if (item.getItemManager() == null) {
            item.setItemManager(getItemManager());
        }
    }

    @Override
    public final void onBindViewHolder(ViewHolder holder, TreeItem item, int position) {
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    TreeItem baseItem = getDatas().get(position);
                    if (baseItem.getSpanSize() == 0) {
                        return gridLayoutManager.getSpanCount();
                    }
                    return baseItem.getSpanSize();
                }
            });
        }
    }

    /**
     * 相应RecyclerView的点击事件 展开或关闭某节点
     */
    private void expandOrCollapse(TreeItemGroup treeItemGroup) {
        boolean expand = treeItemGroup.isExpand();
        treeItemGroup.setExpand(!expand);
        treeItemGroup.notifyExpand();
    }

    /**
     * 需要设置在setdata之前,否则type不会生效
     *
     * @param type
     */
    public void setType(TreeRecyclerType type) {
        this.type = type;
    }

    private class TreeItemManageImpl extends ItemManager<TreeItem> {

        TreeItemManageImpl(BaseRecyclerAdapter<TreeItem> adapter) {
            super(adapter);
        }

        @Override
        public void addItem(TreeItem item) {
            if (null == item) {
                return;
            }
            if (item instanceof TreeItemGroup) {
                getDatas().add(item);
            } else {
                TreeItemGroup itemParentItem = item.getParentItem();
                if (itemParentItem != null) {
                    List childs = itemParentItem.getChilds();
                    if (childs != null) {
                        int i = getDatas().indexOf(itemParentItem);
                        getDatas().add(i + itemParentItem.getChilds().size(), item);
                    } else {
                        childs = new ArrayList();
                        itemParentItem.setChilds(childs);
                    }
                    childs.add(item);
                }
            }
            notifyDataChanged();
        }

        @Override
        public void addItem(int position, TreeItem item) {
            getDatas().add(position, item);
            if (item != null && item.getParentItem() != null) {
                item.getParentItem().getChilds().add(item);
            }
            notifyDataChanged();
        }

        @Override
        public void addItems(List<TreeItem> items) {
            getDatas().addAll(items);
            notifyDataChanged();
        }

        @Override
        public void addItems(int position, List<TreeItem> items) {
            getDatas().addAll(position, items);
            notifyDataChanged();
        }

        @Override
        public void removeItem(TreeItem item) {
            if (null == item) {
                return;
            }
            getDatas().remove(item);
            TreeItemGroup itemParentItem = item.getParentItem();
            if (itemParentItem != null) {
                List childs = itemParentItem.getChilds();
                if (childs != null) {
                    childs.remove(item);
                }
            }
            notifyDataChanged();
        }

        @Override
        public void removeItem(int position) {
            TreeItem t = getDatas().get(position);
            TreeItemGroup parentItem = t.getParentItem();
            if (parentItem != null && parentItem.getChilds() != null) {
                parentItem.getChilds().remove(t);
            }
            getDatas().remove(position);
            notifyDataChanged();
        }

        @Override
        public void removeItems(List<TreeItem> items) {
            getDatas().removeAll(items);
            notifyDataChanged();
        }

        @Override
        public void replaceItem(int position, TreeItem item) {
            TreeItem t = getDatas().get(position);
            if (t instanceof TreeItemGroup) {
                getDatas().set(position, item);
            } else {
                TreeItemGroup parentItem = t.getParentItem();
                if (parentItem != null && parentItem.getChilds() != null) {
                    List childs = parentItem.getChilds();
                    int i = childs.indexOf(t);
                    childs.set(i, item);
                }
                getDatas().set(position, item);
            }
            notifyDataChanged();
        }

        @Override
        public void replaceAllItem(List<TreeItem> items) {
            if (items != null) {
                setDatas(items);
                notifyDataChanged();
            }
        }

        @Override
        public TreeItem getItem(int position) {
            return getDatas().get(position);
        }

        @Override
        public int getItemPosition(TreeItem item) {
            return getDatas().indexOf(item);
        }
    }

}

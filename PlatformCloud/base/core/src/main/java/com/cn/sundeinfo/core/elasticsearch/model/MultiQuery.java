package com.cn.sundeinfo.core.elasticsearch.model;

import java.util.List;

public class MultiQuery<T>
{
    private PagesBase pages;
    private List<SearchListBase<T>> items;

    public void setPages(PagesBase pages)
    {
        this.pages = pages;
    }

    public void setItems(List<SearchListBase<T>> items)
    {
        this.items = items;
    }

    public boolean equals(Object o)
    {
        if (o == this) {
            return true;
        }
        if (!(o instanceof MultiQuery)) {
            return false;
        }
        MultiQuery<?> other = (MultiQuery)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Object this$pages = getPages();Object other$pages = other.getPages();
        if (this$pages == null ? other$pages != null : !this$pages.equals(other$pages)) {
            return false;
        }
        Object this$items = getItems();Object other$items = other.getItems();return this$items == null ? other$items == null : this$items.equals(other$items);
    }

    protected boolean canEqual(Object other)
    {
        return other instanceof MultiQuery;
    }

    public int hashCode()
    {
        int PRIME = 59;int result = 1;Object $pages = getPages();result = result * 59 + ($pages == null ? 43 : $pages.hashCode());Object $items = getItems();result = result * 59 + ($items == null ? 43 : $items.hashCode());return result;
    }

    public String toString()
    {
        return "MultiQuery(pages=" + getPages() + ", items=" + getItems() + ")";
    }

    public PagesBase getPages()
    {
        return this.pages;
    }

    public List<SearchListBase<T>> getItems()
    {
        return this.items;
    }
}

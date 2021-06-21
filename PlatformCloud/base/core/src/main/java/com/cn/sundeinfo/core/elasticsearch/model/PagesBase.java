package com.cn.sundeinfo.core.elasticsearch.model;

import java.util.Map;

public class PagesBase
{
    private Integer from;
    private Integer size;
    private Map<String, String> sort;

    public PagesBase(Integer from, Integer size) {
        this.from = from;
        this.size = size;
    }

    public void setFrom(Integer from)
    {
        this.from = from;
    }

    public void setSize(Integer size)
    {
        this.size = size;
    }

    public void setSort(Map<String, String> sort)
    {
        this.sort = sort;
    }

    public boolean equals(Object o)
    {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PagesBase)) {
            return false;
        }
        PagesBase other = (PagesBase)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Object this$from = getFrom();Object other$from = other.getFrom();
        if (this$from == null ? other$from != null : !this$from.equals(other$from)) {
            return false;
        }
        Object this$size = getSize();Object other$size = other.getSize();
        if (this$size == null ? other$size != null : !this$size.equals(other$size)) {
            return false;
        }
        Object this$sort = getSort();Object other$sort = other.getSort();return this$sort == null ? other$sort == null : this$sort.equals(other$sort);
    }

    protected boolean canEqual(Object other)
    {
        return other instanceof PagesBase;
    }

    public int hashCode()
    {
        int PRIME = 59;int result = 1;Object $from = getFrom();result = result * 59 + ($from == null ? 43 : $from.hashCode());Object $size = getSize();result = result * 59 + ($size == null ? 43 : $size.hashCode());Object $sort = getSort();result = result * 59 + ($sort == null ? 43 : $sort.hashCode());return result;
    }

    public String toString()
    {
        return "PagesBase(from=" + getFrom() + ", size=" + getSize() + ", sort=" + getSort() + ")";
    }

    public Integer getFrom()
    {
        return this.from;
    }

    public Integer getSize()
    {
        return this.size;
    }

    public Map<String, String> getSort()
    {
        return this.sort;
    }
}


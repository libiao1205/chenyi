package com.cn.sundeinfo.core.elasticsearch.model;

public class RangeItem
{
    private RangeInfo from;
    private RangeInfo to;
    private String linkType;

    public void setFrom(RangeInfo from)
    {
        this.from = from;
    }

    public void setTo(RangeInfo to)
    {
        this.to = to;
    }

    public void setLinkType(String linkType)
    {
        this.linkType = linkType;
    }

    public boolean equals(Object o)
    {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RangeItem)) {
            return false;
        }
        RangeItem other = (RangeItem)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Object this$from = getFrom();Object other$from = other.getFrom();
        if (this$from == null ? other$from != null : !this$from.equals(other$from)) {
            return false;
        }
        Object this$to = getTo();Object other$to = other.getTo();
        if (this$to == null ? other$to != null : !this$to.equals(other$to)) {
            return false;
        }
        Object this$linkType = getLinkType();Object other$linkType = other.getLinkType();return this$linkType == null ? other$linkType == null : this$linkType.equals(other$linkType);
    }

    protected boolean canEqual(Object other)
    {
        return other instanceof RangeItem;
    }

    public int hashCode()
    {
        int PRIME = 59;int result = 1;Object $from = getFrom();result = result * 59 + ($from == null ? 43 : $from.hashCode());Object $to = getTo();result = result * 59 + ($to == null ? 43 : $to.hashCode());Object $linkType = getLinkType();result = result * 59 + ($linkType == null ? 43 : $linkType.hashCode());return result;
    }

    public String toString()
    {
        return "RangeItem(from=" + getFrom() + ", to=" + getTo() + ", linkType=" + getLinkType() + ")";
    }

    public RangeInfo getFrom()
    {
        return this.from;
    }

    public RangeInfo getTo()
    {
        return this.to;
    }

    public String getLinkType()
    {
        return this.linkType;
    }
}

package com.cn.sundeinfo.core.elasticsearch.model;

public class RangeInfo
{
    private String type;
    private Object value;

    public void setType(String type)
    {
        this.type = type;
    }

    public void setValue(Object value)
    {
        this.value = value;
    }

    public boolean equals(Object o)
    {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RangeInfo)) {
            return false;
        }
        RangeInfo other = (RangeInfo)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Object this$type = getType();Object other$type = other.getType();
        if (this$type == null ? other$type != null : !this$type.equals(other$type)) {
            return false;
        }
        Object this$value = getValue();Object other$value = other.getValue();return this$value == null ? other$value == null : this$value.equals(other$value);
    }

    protected boolean canEqual(Object other)
    {
        return other instanceof RangeInfo;
    }

    public int hashCode()
    {
        int PRIME = 59;int result = 1;Object $type = getType();result = result * 59 + ($type == null ? 43 : $type.hashCode());Object $value = getValue();result = result * 59 + ($value == null ? 43 : $value.hashCode());return result;
    }

    public String toString()
    {
        return "RangeInfo(type=" + getType() + ", value=" + getValue() + ")";
    }

    public String getType()
    {
        return this.type;
    }

    public Object getValue()
    {
        return this.value;
    }
}

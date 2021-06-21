package com.cn.sundeinfo.core.elasticsearch.model;

import java.util.ArrayList;
import java.util.List;

public class AggerSetting
{
    private String name;
    private String field;

    public void setName(String name)
    {
        this.name = name;
    }

    public void setField(String field)
    {
        this.field = field;
    }

    public void setChild(List<String> child)
    {
        this.child = child;
    }

    public boolean equals(Object o)
    {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AggerSetting)) {
            return false;
        }
        AggerSetting other = (AggerSetting)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Object this$name = getName();Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
            return false;
        }
        Object this$field = getField();Object other$field = other.getField();
        if (this$field == null ? other$field != null : !this$field.equals(other$field)) {
            return false;
        }
        Object this$child = getChild();Object other$child = other.getChild();return this$child == null ? other$child == null : this$child.equals(other$child);
    }

    protected boolean canEqual(Object other)
    {
        return other instanceof AggerSetting;
    }

    public int hashCode()
    {
        int PRIME = 59;int result = 1;Object $name = getName();result = result * 59 + ($name == null ? 43 : $name.hashCode());Object $field = getField();result = result * 59 + ($field == null ? 43 : $field.hashCode());Object $child = getChild();result = result * 59 + ($child == null ? 43 : $child.hashCode());return result;
    }

    public String toString()
    {
        return "AggerSetting(name=" + getName() + ", field=" + getField() + ", child=" + getChild() + ")";
    }

    public String getName()
    {
        return this.name;
    }

    public String getField()
    {
        return this.field;
    }

    public List<String> getChild()
    {
        return this.child;
    }

    private List<String> child = new ArrayList();
}

package com.cn.sundeinfo.core.elasticsearch.model;

import java.util.ArrayList;
import java.util.List;

public class AggerItem
{
    private String name;
    private String field;
    private String key;
    private Object value;

    public void setName(String name)
    {
        this.name = name;
    }

    public void setField(String field)
    {
        this.field = field;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public void setValue(Object value)
    {
        this.value = value;
    }

    public void setChilds(List<AggerItem> childs)
    {
        this.childs = childs;
    }

    public void setResults(List<AggerItem> results)
    {
        this.results = results;
    }

    public boolean equals(Object o)
    {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AggerItem)) {
            return false;
        }
        AggerItem other = (AggerItem)o;
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
        Object this$key = getKey();Object other$key = other.getKey();
        if (this$key == null ? other$key != null : !this$key.equals(other$key)) {
            return false;
        }
        Object this$value = getValue();Object other$value = other.getValue();
        if (this$value == null ? other$value != null : !this$value.equals(other$value)) {
            return false;
        }
        Object this$childs = getChilds();Object other$childs = other.getChilds();
        if (this$childs == null ? other$childs != null : !this$childs.equals(other$childs)) {
            return false;
        }
        Object this$results = getResults();Object other$results = other.getResults();return this$results == null ? other$results == null : this$results.equals(other$results);
    }

    protected boolean canEqual(Object other)
    {
        return other instanceof AggerItem;
    }

    public int hashCode()
    {
        int PRIME = 59;int result = 1;Object $name = getName();result = result * 59 + ($name == null ? 43 : $name.hashCode());Object $field = getField();result = result * 59 + ($field == null ? 43 : $field.hashCode());Object $key = getKey();result = result * 59 + ($key == null ? 43 : $key.hashCode());Object $value = getValue();result = result * 59 + ($value == null ? 43 : $value.hashCode());Object $childs = getChilds();result = result * 59 + ($childs == null ? 43 : $childs.hashCode());Object $results = getResults();result = result * 59 + ($results == null ? 43 : $results.hashCode());return result;
    }

    public String toString()
    {
        return "AggerItem(name=" + getName() + ", field=" + getField() + ", key=" + getKey() + ", value=" + getValue() + ", childs=" + getChilds() + ", results=" + getResults() + ")";
    }

    public String getName()
    {
        return this.name;
    }

    public String getField()
    {
        return this.field;
    }

    public String getKey()
    {
        return this.key;
    }

    public Object getValue()
    {
        return this.value;
    }

    public List<AggerItem> getChilds()
    {
        return this.childs;
    }

    private List<AggerItem> childs = new ArrayList();

    public List<AggerItem> getResults()
    {
        return this.results;
    }

    private List<AggerItem> results = new ArrayList();
}

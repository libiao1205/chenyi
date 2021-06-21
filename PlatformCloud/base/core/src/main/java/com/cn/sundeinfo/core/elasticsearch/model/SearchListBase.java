package com.cn.sundeinfo.core.elasticsearch.model;

public class SearchListBase<T>
{
    private String searchType;
    private String searchOper;
    private T item;
    private String key;
    private String value;
    private String oper;
    private String type;
    private RangeItem range;
    private Integer boost = 1;

    public void setSearchType(String searchType)
    {
        this.searchType = searchType;
    }

    public void setOper(String oper)
    {
        this.oper = oper;
    }

    public void setItem(T item)
    {
        this.item = item;
    }

    public boolean equals(Object o)
    {
        if (o == this) {
            return true;
        }
        if (!(o instanceof SearchListBase)) {
            return false;
        }
        SearchListBase<?> other = (SearchListBase)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Object this$searchType = getSearchType();Object other$searchType = other.getSearchType();
        if (this$searchType == null ? other$searchType != null : !this$searchType.equals(other$searchType)) {
            return false;
        }
        Object this$oper = getOper();Object other$oper = other.getOper();
        if (this$oper == null ? other$oper != null : !this$oper.equals(other$oper)) {
            return false;
        }
        Object this$item = getItem();Object other$item = other.getItem();return this$item == null ? other$item == null : this$item.equals(other$item);
    }

    protected boolean canEqual(Object other)
    {
        return other instanceof SearchListBase;
    }

    public int hashCode()
    {
        int PRIME = 59;int result = 1;Object $searchType = getSearchType();result = result * 59 + ($searchType == null ? 43 : $searchType.hashCode());Object $oper = getOper();result = result * 59 + ($oper == null ? 43 : $oper.hashCode());Object $item = getItem();result = result * 59 + ($item == null ? 43 : $item.hashCode());return result;
    }

    public String toString()
    {
        return "SearchListBase(searchType=" + getSearchType() + ", oper=" + getOper() + ", item=" + getItem() + ")";
    }

    public String getSearchType()
    {
        return this.searchType;
    }

    public String getOper()
    {
        return this.oper;
    }

    public T getItem()
    {
        return (T)this.item;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSearchOper() {
        return searchOper;
    }

    public void setSearchOper(String searchOper) {
        this.searchOper = searchOper;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getBoost() {
        return boost;
    }

    public void setBoost(Integer boost) {
        this.boost = boost;
    }

    public RangeItem getRange() {
        return range;
    }

    public void setRange(RangeItem range) {
        this.range = range;
    }
}

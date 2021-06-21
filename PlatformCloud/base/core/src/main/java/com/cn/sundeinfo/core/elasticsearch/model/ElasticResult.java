package com.cn.sundeinfo.core.elasticsearch.model;

import java.util.List;

public class ElasticResult<T>
{
    private Long totle;
    private List rows;

    public void setTotle(Long totle)
    {
        this.totle = totle;
    }

    public void setRows(List rows)
    {
        this.rows = rows;
    }

    public boolean equals(Object o)
    {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ElasticResult)) {
            return false;
        }
        ElasticResult<?> other = (ElasticResult)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Object this$totle = getTotle();Object other$totle = other.getTotle();
        if (this$totle == null ? other$totle != null : !this$totle.equals(other$totle)) {
            return false;
        }
        Object this$rows = getRows();Object other$rows = other.getRows();return this$rows == null ? other$rows == null : this$rows.equals(other$rows);
    }

    protected boolean canEqual(Object other)
    {
        return other instanceof ElasticResult;
    }

    public int hashCode()
    {
        int PRIME = 59;int result = 1;Object $totle = getTotle();result = result * 59 + ($totle == null ? 43 : $totle.hashCode());Object $rows = getRows();result = result * 59 + ($rows == null ? 43 : $rows.hashCode());return result;
    }

    public String toString()
    {
        return "ElasticResult(totle=" + getTotle() + ", rows=" + getRows() + ")";
    }

    public Long getTotle()
    {
        return this.totle;
    }

    public List<T> getRows()
    {
        return this.rows;
    }
}


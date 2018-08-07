package by.training.hotel.entity.data_transfer_object;

import by.training.hotel.entity.Entity;

import java.util.List;
import java.util.Objects;

public final class CommonDTO<E extends Entity> implements Entity {

    private static final long serialVersionUID = 5628757903502077096L;

    private List<E> entityList;

    private Integer pagesCount;

    public List<E> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<E> entityList) {
        this.entityList = entityList;
    }

    public Integer getPagesCount() {
        return pagesCount;
    }

    public void setPagesCount(Integer pagesCount) {
        this.pagesCount = pagesCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CommonDTO<?> commonDTO = (CommonDTO<?>) o;
        return Objects.equals(getEntityList(), commonDTO.getEntityList()) &&
                Objects.equals(getPagesCount(), commonDTO.getPagesCount());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getEntityList(), getPagesCount());
    }

    @Override
    public String toString() {
        return "CommonDTO{" +
                "entityList=" + entityList +
                ", pagesCount=" + pagesCount +
                '}';
    }
}

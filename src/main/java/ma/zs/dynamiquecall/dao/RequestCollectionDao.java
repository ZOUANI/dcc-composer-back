package ma.zs.dynamiquecall.dao;


import ma.zs.dynamiquecall.bean.RequestCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RequestCollectionDao extends JpaRepository<RequestCollection, Long> {
    public RequestCollection findByName(String name);
}

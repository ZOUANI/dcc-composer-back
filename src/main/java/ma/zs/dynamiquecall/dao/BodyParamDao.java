package ma.zs.dynamiquecall.dao;


import ma.zs.dynamiquecall.bean.BodyParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BodyParamDao extends JpaRepository<BodyParam, Long> {
}

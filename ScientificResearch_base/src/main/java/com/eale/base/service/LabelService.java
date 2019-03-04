package com.eale.base.service;

import com.eale.base.repository.LabelRepository;
import com.eale.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class LabelService {
    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private IdWorker idWorker;

    public List<Label> findAll(){
        return labelRepository.findAll();
    }

    public Label findById(String id){
        return labelRepository.findById(id).get();
    }

    public void save(Label label){
        label.setId(idWorker.nextId()+"");
        labelRepository.save(label);
    }

    public void update(Label label){
        labelRepository.save(label);
    }

    public void deleteById(String id){
        labelRepository.deleteById(id);
    }

    public List<Label> findSearch(Label label) {
        return labelRepository.findAll(new Specification<Label>() {
            /**
             * @param root 根对象,也就是要把条件装到哪个对象中,  where 类名=label.getId
             * @param query 封装的都是查询关键字,比如group by  order by
             * @param cb 用来封装条件对象的,如果直接返回null,表示
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                //new list 集合来存放所有的条件
                List<Predicate> list=new ArrayList<>();
                if(label.getLabelname()!=null && !"".equals(label.getLabelname())){
                    Predicate predicate = cb.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
                    list.add(predicate);
                }
                if(label.getState()!=null && !"".equals(label.getState())){
                    Predicate predicate = cb.equal(root.get("state").as(String.class),   label.getState());
                    list.add(predicate);
                }
                //new 一个数组作为最终返回值的条件
                Predicate[] parr=new Predicate[list.size()];
                parr = list.toArray(parr);
                return cb.and(parr);
            }
        });
    }

    public Page<Label> pageQuery(Label label, int page, int size) {

        //封装一个分页对象
        Pageable pageable= PageRequest.of(page-1,size);
        return labelRepository.findAll(new Specification<Label>() {
            /**
             * @param root 根对象,也就是要把条件装到哪个对象中,  where 类名=label.getId
             * @param query 封装的都是查询关键字,比如group by  order by
             * @param cb 用来封装条件对象的,如果直接返回null,表示
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                //new list 集合来存放所有的条件
                List<Predicate> list=new ArrayList<>();
                if(label.getLabelname()!=null && !"".equals(label.getLabelname())){
                    Predicate predicate = cb.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
                    list.add(predicate);
                }
                if(label.getState()!=null && !"".equals(label.getState())){
                    Predicate predicate = cb.equal(root.get("state").as(String.class),   label.getState());
                    list.add(predicate);
                }
                //new 一个数组作为最终返回值的条件
                Predicate[] parr=new Predicate[list.size()];
                parr = list.toArray(parr);
                return cb.and(parr);
            }
        },pageable);
    }
}

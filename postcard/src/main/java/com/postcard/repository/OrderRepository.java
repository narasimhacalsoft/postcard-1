package com.postcard.repository;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.postcard.dao.BaseDao;
import com.postcard.model.PostcardOrder;
import com.postcard.model.Postcard;

@Repository
public class OrderRepository extends BaseDao {}

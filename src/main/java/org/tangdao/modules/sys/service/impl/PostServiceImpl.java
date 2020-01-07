package org.tangdao.modules.sys.service.impl;

import org.springframework.stereotype.Service;

import org.tangdao.common.service.impl.CrudServiceImpl;
import org.tangdao.modules.sys.service.IPostService;
import org.tangdao.modules.sys.model.domain.Post;
import org.tangdao.modules.sys.mapper.PostMapper;

/**
 * 岗位ServiceImpl
 * @author ruyang
 * @version 2019-08-23
 */
@Service
public class PostServiceImpl extends CrudServiceImpl<PostMapper, Post> implements IPostService{
		
}
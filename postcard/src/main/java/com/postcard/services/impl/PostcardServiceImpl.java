package com.postcard.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.postcard.dao.PostcardDao;
import com.postcard.exception.ServiceException;
import com.postcard.model.Postcard;
import com.postcard.model.RecipientAddress;
import com.postcard.model.SaveRecipientRequest;
import com.postcard.model.SaveRecipientResponse;
import com.postcard.service.PostcardService;

@Service
public class PostcardServiceImpl implements PostcardService{
    
    @Autowired
    PostcardDao postcardDao;
    

    public Long createPostcard(final Postcard postcard) {
        postcardDao.createPostcard(postcard);
        return null;        
    }

    public Long updatePostcard(Postcard postcard) {
        postcardDao.updatePostcard(postcard);
        return null;
    }


    public List<Postcard> findallPostcard() {
        List<Postcard> postcards = postcardDao.findallPostcard();
        return postcards;
    }

    public Postcard findOne(Long card_id) {
        Postcard postcard = postcardDao.findOne(card_id);
        return postcard;
    }


    public void deletePostcard(Postcard postcard) {
        postcardDao.deletePostcard(postcard);        
    }

	@Override
	public SaveRecipientResponse saveRecipientAddress(SaveRecipientRequest request) throws ServiceException {
		for (RecipientAddress address : request.getRecipients()) {
			if(!CollectionUtils.isEmpty(address.getErrors())) {
				throw new ServiceException("Invalid Recipient Address");
			}
		}
		return postcardDao.saveRecipientAddress(request);
	}

}

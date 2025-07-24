package com.promotick.lafabril.web.service.impl;

import com.promotick.lafabril.dao.web.WishlistDao;
import com.promotick.lafabril.model.web.Wishlist;
import com.promotick.lafabril.web.service.WishlistWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WishlistWebServiceImpl implements WishlistWebService {

    private WishlistDao wishlistDao;

    @Autowired
    public WishlistWebServiceImpl(WishlistDao wishlistDao) {
        this.wishlistDao = wishlistDao;
    }

    @Override
    public List<Wishlist> listarWishlist(Integer idParticipante) {
        return wishlistDao.listarWishlist(idParticipante);
    }

    @Override
    @Transactional
    public Integer updateWishlist(Wishlist wishlist) {
        return wishlistDao.updateWishlist(wishlist);
    }
}

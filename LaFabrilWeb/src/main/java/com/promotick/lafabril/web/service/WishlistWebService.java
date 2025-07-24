package com.promotick.lafabril.web.service;

import com.promotick.lafabril.model.web.Wishlist;

import java.util.List;

public interface WishlistWebService {
    List<Wishlist> listarWishlist(Integer idParticipante);

    Integer updateWishlist(Wishlist wishlist);
}

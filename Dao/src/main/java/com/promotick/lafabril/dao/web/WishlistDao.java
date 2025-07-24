package com.promotick.lafabril.dao.web;

import com.promotick.lafabril.model.web.Wishlist;

import java.util.List;

public interface WishlistDao {
    List<Wishlist> listarWishlist(Integer idParticipante);

    Integer updateWishlist(Wishlist wishlist);
}

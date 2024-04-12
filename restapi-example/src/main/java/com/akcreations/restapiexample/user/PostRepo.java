package com.akcreations.restapiexample.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepo extends JpaRepository<Post, Integer> {

}

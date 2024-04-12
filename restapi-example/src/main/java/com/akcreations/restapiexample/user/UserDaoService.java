package com.akcreations.restapiexample.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public class UserDaoService {

	
	private static List<User> users =new ArrayList<User>();
	private static int count=0;
	
	static {
		users.add(new User(++count,"Adam",LocalDate.now().minusYears(30)));
		users.add(new User(++count,"Adam2",LocalDate.now().minusYears(20)));
		users.add(new User(++count,"Adam3",LocalDate.now().minusYears(10)));
	}
	
	public List<User> findAllUsers(){
		return users;
	}
	
	public User addUser(User newUser) {
		newUser.setId(++count);
		users.add(newUser);
		return newUser;
	}
	
	public Optional<User> findUser(int userId){
		 
		return users.stream().filter(userItem-> userItem.getId()==userId).findFirst();
	}

	public void deleteById(int id) {
		User user = findUser(id).orElse(null);
		users.remove(user);
	}

}

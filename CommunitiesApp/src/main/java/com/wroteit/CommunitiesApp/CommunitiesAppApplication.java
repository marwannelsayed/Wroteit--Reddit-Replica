package com.wroteit.CommunitiesApp;

import com.wroteit.CommunitiesApp.model.Community;
import com.wroteit.CommunitiesApp.repository.CommunityRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

import java.util.List;

@SpringBootApplication
public class CommunitiesAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommunitiesAppApplication.class, args);
	}
	@Service
	public class UserDeletionListener {

		private final CommunityRepository communityRepository;

		public UserDeletionListener(CommunityRepository communityRepository) {
			this.communityRepository = communityRepository;
		}

		@RabbitListener(queues = "deleteUserQueue")
		public void handleUserDeletion(Long userId) {
			List<Community> communities = communityRepository.findAll();
			for(Community c: communities){
				c.getSubscribers().remove(userId);
				communityRepository.save(c);
			}
		}
	}

}

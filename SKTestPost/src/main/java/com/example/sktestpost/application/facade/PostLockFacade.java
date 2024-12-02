package com.example.sktestpost.application.facade;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import com.example.sktestpost.application.port.in.PostLockUseCase;
import com.example.sktestpost.common.dto.response.GetPostResDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostLockFacade implements PostLockUseCase {

	private final PostFacade postFacade;
	private final RedissonClient redissonClient;
	private static final long LOCK_WAIT_TIME = 10L;   // 락 대기 시간 (초)
	private static final long LOCK_LEASE_TIME = 5L;

	// public GetPostResDTO getPost(Long postId) {
	// 	String lockKey = "posting:" + postId;
	// 	RLock rLock = redissonClient.getLock(lockKey);
	// 	boolean isLocked = false;
	//
	// 	try {
	// 		isLocked = rLock.tryLock(LOCK_WAIT_TIME, LOCK_LEASE_TIME, TimeUnit.SECONDS);
	// 		if (!isLocked) {
	// 			log.info("Lock 획득 실패: {}", lockKey);
	// 			throw new RuntimeException("해당 게시물에 대한 락을 획득할 수 없습니다.");
	// 		}
	// 		log.info("Lock 획득 완료: {}", lockKey);
	// 		return postFacade.getPost(postId);
	// 	} catch (InterruptedException e) {
	// 		Thread.currentThread().interrupt(); // 인터럽트 상태 복원
	// 		log.error("Lock 처리 중 인터럽트 발생: {}", e.getMessage(), e);
	// 		throw new RuntimeException("Lock 처리 중 문제가 발생했습니다.", e);
	// 	} finally {
	// 		// 현재 스레드가 락을 보유한 경우에만 해제
	// 		if (isLocked && rLock.isHeldByCurrentThread()) {
	// 			rLock.unlock();
	// 			log.info("Lock 해제 완료: {}", lockKey);
	// 		}
	// 	}
	// }
	public GetPostResDTO getPost(Long postId) {
		String lockKey = "posting:" + postId;
		RLock rLock = redissonClient.getLock(lockKey);
		boolean isLocked = false;

		try {
			isLocked = rLock.tryLock(LOCK_WAIT_TIME, LOCK_LEASE_TIME, TimeUnit.SECONDS);
			if (!isLocked) {
				log.info("Lock 획득 실패: {}", lockKey);
				throw new RuntimeException("해당 게시물에 대한 락을 획득할 수 없습니다.");
			}
			log.info("Lock 획득 완료: {}", lockKey);
			return postFacade.getPost(postId);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt(); // 인터럽트 상태 복원
			log.error("Lock 처리 중 인터럽트 발생: {}", e.getMessage(), e);
			throw new RuntimeException("Lock 처리 중 문제가 발생했습니다.", e);
		} finally {
			// 현재 스레드가 락을 보유한 경우에만 해제
			if (isLocked && rLock.isHeldByCurrentThread()) {
				rLock.unlock();
				log.info("Lock 해제 완료: {}", lockKey);
			}
		}
	}

}

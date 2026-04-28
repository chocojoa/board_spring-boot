package com.lollipop.board.setup.aspect;

import com.lollipop.board.setup.model.ApiResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceAspect {

    @Around(value = "execution(* com.lollipop.board.*.controller.*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis(); // 처리 시작 시간
        Object proceed = joinPoint.proceed(); // 실제 메서드 실행
        long processingTime = System.currentTimeMillis() - startTime; // 처리 시간 계산

        // 반환 타입이 ResponseEntity 경우에만 처리
        if (proceed instanceof ResponseEntity<?> responseEntity) {

            if (responseEntity.getBody() instanceof ApiResponse<?> apiResponse) {
                apiResponse.setProcessingTimeMs(processingTime);
            }
        }

        return proceed;
    }
}

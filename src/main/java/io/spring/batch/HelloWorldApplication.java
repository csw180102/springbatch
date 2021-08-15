package io.spring.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@EnableBatchProcessing  // 요 어노테이션이 "배치 인프라스트럭처"에 관한 대부분의 스프링 빈(미리 정의되어 있는)을 제공한다.
@SpringBootApplication
public class HelloWorldApplication {

	//잡빌더 
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	//스텝빌더
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	//스텝 bean
	@Bean
	public Step step() {
		//잡리포티터리, 트랜잭션매니저  객체라고 해야되냐 뭐 공간? 이런곳에 다 아래 내용들이 저장되나봄 그래서 
		return this.stepBuilderFactory.get("step1")
						.tasklet(new Tasklet() {
									@Override
									public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
										System.out.println("▶▶▶▶▶▶▶Spring Batch▶▶▶▶▶▶▶▶  Hello, World! ◀◀◀◀◀◀◀◀Spring Batch◀◀◀◀◀◀◀");
										return RepeatStatus.FINISHED;
									}
						}).build();
	}
	
	//잡 bean
	@Bean
	public Job job() {
		//잡리포지터리에 접근해서 job 이름으로된 리포티터리쪽 객체 접근? 그리고 연결된? step() 메소드를 접근하여 build 한다  대충 이런 flow 같은디..
		return this.jobBuilderFactory.get("job").start(step()).build();
	}
	
	
	
	public static void main(String[] args) {
		SpringApplication.run(HelloWorldApplication.class, args);
	}

}

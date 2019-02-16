package com.sungjun.springrecipesbatch.user;

import com.sungjun.springrecipesbatch.processor.UserRegistrationValidationItemProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.util.ClassUtils;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class UserJob {

    private static final String INSERT_REGISTRATION_QUERY =
            "insert into USER_REGISTRATION (FIRST_NAME, LAST_NAME, COMPANY, ADDRESS,CITY,STATE,ZIP,COUNTY,URL,PHONE_NUMBER,FAX)" +
                    " values " +
                    "(:firstName,:lastName,:company,:address,:city,:state,:zip,:county,:url,:phoneNumber,:fax)";

    private final JobBuilderFactory jobs;
    private final StepBuilderFactory steps;
    private final DataSource dataSource;

    @Value("classpath:registrations.csv")
    private Resource input;

    @Bean
    public Job insertIntoDbFromCsvJob() {
        return jobs.get("User Registration Import Job")
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() {
        return steps.get("User Registration CSV To DB Step")
                .<UserRegistration, UserRegistration>chunk(5)
                .reader(csvFileReader())
                .processor(userRegistrationValidationItemProcessor())
                .writer(jdbcItemWriter())
                .build();
    }

    @Bean
    public FlatFileItemReader<UserRegistration> csvFileReader() {
        return new FlatFileItemReaderBuilder<UserRegistration>()
                .name(ClassUtils.getShortName(FlatFileItemReader.class))
                .resource(input)
                .targetType(UserRegistration.class)
                .delimited()
                .names(new String[]{"firstName", "lastName", "company", "address", "city", "state", "zip", "county", "url", "phoneNumber", "fax"})
                .build();
    }

    @Bean
    public ItemProcessor<UserRegistration, UserRegistration> userRegistrationValidationItemProcessor() {
        return new UserRegistrationValidationItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<UserRegistration> jdbcItemWriter() {
        return new JdbcBatchItemWriterBuilder<UserRegistration>()
                .dataSource(dataSource)
                .sql(INSERT_REGISTRATION_QUERY)
                .beanMapped()
                .build();
    }

}

package com.cognizant.cars_shop.config;

import com.cognizant.cars_shop.domain.Warehouse;
import com.cognizant.cars_shop.repository.WarehouseRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;

@Configuration // Informs Spring that this class contains configurations
@EnableBatchProcessing // Enables batch processing for the application
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Lazy
    public WarehouseRepository warehouseRepository;

    // Reads the sample-data.csv file and creates instances of the Person entity for each person from the .csv file.
    @Bean
    public FlatFileItemReader<Warehouse> reader() {
        return new FlatFileItemReaderBuilder<Warehouse>()
            .name("warehouseReader")
            .resource(new ClassPathResource("data.csv"))
            .delimited()
            .names("name", "locationLat", "locationLong")
            .fieldSetMapper(new BeanWrapperFieldSetMapper<Warehouse>() {{
                setTargetType(Warehouse.class);
            }})
            .build();
    }

    // Creates the Writer, configuring the repository and the method that will be used to save the data into the database
    // Batch jobs are built from steps. A step contains the reader and the writer.
    @Bean
    public Step step1(ItemReader<Warehouse> itemReader, ItemWriter<Warehouse> itemWriter)
        throws Exception {

        return this.stepBuilderFactory.get("step1")
            .<Warehouse, Warehouse>chunk(5)
            .reader(itemReader)
            .writer(itemWriter)
            .build();
    }

    @Bean
    public RepositoryItemWriter<Warehouse> writer() {
        RepositoryItemWriter<Warehouse> iwriter = new RepositoryItemWriter<>();
        iwriter.setRepository(warehouseRepository);
        iwriter.setMethodName("save");
        return iwriter;
    }

    // Executes the job, saving the data from .csv file into the database.
    @Bean
    public Job customerUpdateJob(JobCompletionNotificationListener listener, Step step1)
        throws Exception {

        return this.jobBuilderFactory.get("warehouseUpdateJob").incrementer(new RunIdIncrementer())
            .listener(listener).start(step1).build();
    }
}

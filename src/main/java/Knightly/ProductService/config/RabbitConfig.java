package Knightly.ProductService.config;

import Knightly.ProductService.microservices.MicroServiceClient;
import com.rabbitmq.client.RpcClient;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${currency.queue.name}")
    private String currencyQueueName;

    @Value("${xchange.name}")
    private String directXchangeName;

    @Value("${price.queue.name}")
    private String priceQueueName;

    @Value("${product.queue.name}")
    private String productQueueName;

    @Value("${routing.key.currency.service}")
    private String routingKeyCurrencyService;

    @Value("${routing.key.price.service}")
    private String routingKeyPriceService;

    @Value("${routing.key.product.service}")
    private String routingKeyProductService;

    @Bean
    public Queue currencyQueue() {
        return new Queue(currencyQueueName);
    }

    @Bean
    public Queue priceQueue() {
        return new Queue(priceQueueName);
    }

    @Bean
    public Queue productQueue() {
        return new Queue(productQueueName);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(directXchangeName);
    }

    @Bean
    public Binding currencyBinding(DirectExchange directExchange, Queue currencyQueue) {
        return BindingBuilder.bind(currencyQueue).to(directExchange).with(routingKeyCurrencyService);
    }

    @Bean
    public Binding priceBinding(DirectExchange priceDirectExchange, Queue priceQueue) {
        return BindingBuilder.bind(priceQueue).to(priceDirectExchange).with(routingKeyPriceService);
    }

    @Bean
    public Binding productBinding(DirectExchange directExchange, Queue productQueue) {
        return BindingBuilder.bind(productQueue).to(directExchange).with(routingKeyProductService);
    }

    @Bean
    public MicroServiceClient microServiceClient() {
        return new MicroServiceClient();
    }
}

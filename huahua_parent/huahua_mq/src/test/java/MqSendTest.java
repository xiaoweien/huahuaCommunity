import com.huahua.mq.MqApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
/**
 * 功能描述：生产者
 * @Author LiHuaMing
 * @Date 20:08 2019/4/23
 * @Param 
 * @return 
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MqApplication.class)
public class MqSendTest {

    @Autowired private RabbitTemplate rabbitTemplate;

    @Test
    public void testSend(){
        rabbitTemplate.convertAndSend("huahua","","我想LOL");
    }

    @Test
    public void testSend2(){
        rabbitTemplate.convertAndSend("huahua_topic","article.123.123.log","我想LOL");
    }
}

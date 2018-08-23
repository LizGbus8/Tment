package protobuf.analysis;

import com.google.protobuf.Message;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;

/**
 * 作者：LizG on 2018/8/23 21:12
 * 描述：消息解析器
 */
@Slf4j
public class ParseMap {

    /**
     * 函数式接口
     */
    @FunctionalInterface
    public interface Parsing {
        Message process(byte[] bytes) throws IOException;
    }

    public static HashMap<Integer, ParseMap.Parsing> parseMap = new HashMap<>();
    public static HashMap<Class<?>, Integer> msgToptoNum = new HashMap<>();

    public static void register(int ptoNum, ParseMap.Parsing parse, Class<?> cla) {
        if (parseMap.get(ptoNum) == null) {
            parseMap.put(ptoNum, parse);
        } else {
            log.error("srv has been registered in parseMap,ptoNum:{}", ptoNum);
            return;
        }

        if (msgToptoNum.get(cla) == null) {
            msgToptoNum.put(cla, ptoNum);
        } else {
            log.error("srv has been registered in msgToptoNum,ptoNum:{}", ptoNum);
            return;
        }
    }

    public static Message getMessage(int ptoNum, byte[] bytes) throws IOException {
        Parsing parser = parseMap.get(ptoNum);
        if (parser == null) {
            log.error("Unknown Service Num:{}", ptoNum);
        }

        Message msg = parser.process(bytes);

        return msg;
    }

    public static Integer getPtoNum(Message msg){
        return getPtoNum(msg.getClass());
    }

    public static Integer getPtoNum(Class<?> cla){
        return msgToptoNum.get(cla);
    }
}

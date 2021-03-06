package com.tom.waterqualityex.model.local;

import com.tom.waterqualityex.model.HomeResponse;

import java.util.ArrayList;

/**
 * Created by mengxin on 17-3-26.
 */

public class HomeLocalResponse implements HomeResponse {

    ArrayList<String> homeContent = new ArrayList<String>();

    public HomeLocalResponse() {
        homeContent.clear();
        homeContent.add("\t\t浙江农林大学位于杭州西郊临安市，杭州城西科创大走廊的西端，是浙江省唯一的省属本科农林类高校，" +
                "是浙江省人民政府和国家林业局共建高校。学校创建于1958年，时称天目林学院，1966年更名为浙江林学院，" +
                "2010年更名为浙江农林大学。经过近60年的建设，学校已发展成为以农林、生物环境学科为特色，涵盖八大学科门类的多科性大学。");

        homeContent.add("\t\t学校现有东湖、衣锦、诸暨3个校区，占地面积3200余亩，校舍建筑面积90余万平方米，仪器设备总值逾2.3亿元。" +
                "学校设有17个学院（部），62个本科专业，其中国家级特色专业4个，国家优势专业2个，国家卓越农林教育人才培养计划专业7个，" +
                "省级重点专业12个，省级优势专业5个，省级新兴特色专业6个；现有浙江省一流学科（A类）3个，浙江省一流学科(B类)9个；国家林业局" +
                "重点学科与重点培育学科5个；服务国家特殊需求博士人才培养项目1项.。");
        homeContent.add("\t\t长期以来，学校始终秉持“求真、敬业”的校训，弘扬“坚韧不拔、不断超越”的学校精神，" +
                "践行“绿水青山就是金山银山”科学论断，着力培养学生的生态文明意识、创新精神和创业能力，为社会" +
                "输送了一大批具有“肯干、实干、能干”品质的高素质人才。近年来，获得国家级教学成果二等奖2项，省级" +
                "优秀教学成果一等奖6项。有国家级课程6门，省级精品课程23门。建有浙江省人才培养模式创新实验区及7个" +
                "省级实验教学示范中心。");
        homeContent.add("东湖水环境监测治理工程\n" +
                "（1）提升东湖水质，减少“水华”发生频次，改善生物生境条件，重建结构完整的水生态系统；\n" +
                "（2）提升东湖生态景观，体现亲水和谐性，为我校师生和临安当地居民提供高品质的休闲娱乐场所；\n" +
                "（3）研发东湖水环境监测数据平台，建成综合性、生态性的大学生实习实训基地。");
    }

    @Override
    public ArrayList<String> getHomeContent() {
        return homeContent;
    }
}

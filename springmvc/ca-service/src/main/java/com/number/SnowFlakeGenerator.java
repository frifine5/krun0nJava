package com.number;

/**
 * @author WangChengyu
 * 2019/2/12 9:27
 * 雪花算法发号器
 */
public class SnowFlakeGenerator {

    /** 起始的时间戳   设定未应用首版打包前获取的较近的时间戳，可以适当增加可使用的时间范围 */
    private final static long START_STAMP = 1553585553938L;
    /** 可分配的位数  */
    private final static int REMAIN_BIT_NUM = 22;

    /** idc编号 */
    private long idcId;

    /** 机器编号 */
    private long machineId;

    /** 当前序列号 */
    private long sequence = 0L;

    /** 上次最新时间戳  */
    private long lastStamp = -1L;

    /** idc偏移量：一次计算出，避免重复计算 */
    private int idcBitLeftOffset;

    /** 机器id偏移量：一次计算出，避免重复计算 */
    private int machineBitLeftOffset;

    /** 时间戳偏移量：一次计算出，避免重复计算 */
    private int timestampBitLeftOffset;

    /** 最大序列值：一次计算出，避免重复计算 */
    private int maxSequenceValue;


    private SnowFlakeGenerator(int idcBitNum, int machineBitNum, long idcId, long machineId) {
        int sequenceBitNum = REMAIN_BIT_NUM - idcBitNum - machineBitNum;
        if (idcBitNum <= 0 || machineBitNum <= 0 || sequenceBitNum <= 0) {
            throw new IllegalArgumentException("error bit number");
        }
        this.maxSequenceValue = ~(-1 << sequenceBitNum);
        machineBitLeftOffset = sequenceBitNum;
        idcBitLeftOffset = idcBitNum + sequenceBitNum;
        timestampBitLeftOffset = idcBitNum + machineBitNum + sequenceBitNum;
        this.idcId = idcId;
        this.machineId = machineId;
    }


    public synchronized long nextId(){
        long currentStamp = getTimeMill();
        if(currentStamp < lastStamp){
            throw new RuntimeException(String.format("时钟倒退，拒绝发号[%d 毫秒]", (lastStamp - currentStamp)));
        }else if(currentStamp == lastStamp){
            sequence = (sequence + 1) & this.maxSequenceValue;
            if(sequence == 0L){
                lastStamp = tilNextMillis();
//				throw new IllegalStateException("sequence over flow");
            }
        }else{
            sequence = 0L;
        }
        lastStamp = currentStamp;
        return (currentStamp - START_STAMP) << timestampBitLeftOffset
                | idcId << idcBitLeftOffset | machineId << machineBitLeftOffset
                | sequence;

    }


    private long getTimeMill() { return System.currentTimeMillis(); }

    private long tilNextMillis() {
        long timestamp = getTimeMill();
        while (timestamp <= lastStamp) {
            timestamp = getTimeMill();
        }
        return timestamp;
    }



    public static class Factory {

        // 每一部分占位默认长度(bit)
        private final static int DEF_MACHINE_bLEN = 5;
        private final static int DEF_IDC_bLEN = 5;

        private int machineBitNum;
        private int idcBitNum;

        public Factory() {
            this.idcBitNum = DEF_IDC_bLEN;
            this.machineBitNum = DEF_MACHINE_bLEN;
        }

        /**
         * @param idcBitNum 数据中心bit长度
         * @param machineBitNum 机器码bit长度
         */
        public Factory( int idcBitNum, int machineBitNum) {
            this.idcBitNum = idcBitNum;
            this.machineBitNum = machineBitNum;
        }

        /**
         *
         * @param idcId 数据中心编码
         * @param machineId 机器编码
         * @return
         */
        public SnowFlakeGenerator create(long idcId, long machineId) {
            return new SnowFlakeGenerator(this.idcBitNum, this.machineBitNum, idcId, machineId);
        }

    }




    public static void main(String[] args) {
        SnowFlakeGenerator sfg = new SnowFlakeGenerator.Factory().create(0,0);
        long id0 = sfg.nextId();
        System.out.println(id0);
        int i = 0;
        while( i < 100){
            i++;
            System.out.print(sfg.nextId());
            System.out.print(" ");
            if(i%10 == 0)System.out.println();
        }
        System.out.println((id0+"").length());
        System.out.println(System.currentTimeMillis());


    }


}

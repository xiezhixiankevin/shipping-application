package cn.itcast.feign.pojo.pack;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarrierInfoPack {
    private int smallTrunkWaiting;
    private int smallTrunkInTransit;


    private int bigTrunkWaiting;
    private int bigTrunkInTransit;


    private int aircraftWaiting;
    private int aircraftInTransit;

}

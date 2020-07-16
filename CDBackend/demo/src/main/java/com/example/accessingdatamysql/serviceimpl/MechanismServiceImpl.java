package com.example.accessingdatamysql.serviceimpl;

import com.example.accessingdatamysql.service.*;
import org.springframework.stereotype.Service;
import com.example.accessingdatamysql.Classes.*;

@Service
public class MechanismServiceImpl implements MechanismService {
    // @Autowired
    // private CardDao CardDao;

    @Override
    public Integer drawCard(Integer chi, Integer mat, Integer eng) {

        // LotteSpring(subject rarity)

        final int[][][] lotteSpring = new int[Subject.values().length][5][]; // subject, rarity
        final int SUM = chi + mat + eng;
        final int RAND = (int) (Math.random() * 1000.0);
        int rarity;
        if (SUM <= 5) {
            if (RAND < 900 - 10 * SUM)
                rarity = 0;
            else
                rarity = 1;
        } else if (SUM <= 20) {
            if (RAND < 700 - 10 * SUM)
                rarity = 0;
            else if (RAND < 900 - 3 * SUM)
                rarity = 1;
            else
                rarity = 2;
        } else if (SUM <= 50) {
            if (RAND < 600 - 10 * SUM)
                rarity = 0;
            else if (RAND < 800 - 4 * SUM)
                rarity = 1;
            else if (RAND < 950 - 1 * SUM)
                rarity = 2;
            else
                rarity = 3;
        } else if (SUM <= 100) {
            if (RAND < 700 - 6 * SUM)
                rarity = 1;
            else if (RAND < 1500 - 10 * SUM)
                rarity = 2;
            else if (RAND < 1150 - 3 * SUM)
                rarity = 3;
            else
                rarity = 4;
        } else {
            System.out.println("too much bet bug?");
            rarity = 4;
        }
        final int RAND2 = (int) (Math.random() * SUM);
        int subject;
        if (RAND2 < chi)
            subject = 0;
        else if (RAND2 < chi + mat)
            subject = 1;
        else
            subject = 2;
        final int[] group = lotteSpring[subject][rarity];
        return group[(int) (Math.random() * group.length)];
    }
}

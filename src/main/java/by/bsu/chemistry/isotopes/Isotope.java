package by.bsu.chemistry.isotopes;


import by.bsu.chemistry.util.Helper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by Ivan on 03.09.2017.
 */
@Entity
@Table(name = "nuclides")
@Getter
@Setter
@NoArgsConstructor
public class Isotope {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nucid;
    private int z;
    private int n;
    private String symbol;
    private Integer l_seqno;
    private String jp;
    private String half_life;
    private String half_life_unc;
    private String half_life_unit;
    private Double half_life_sec;
    private String mass_excess;
    private String mass_excess_unc;
    private String binding;
    private String binding_unc;
    private String atomic_mass;
    private String atomic_mass_unc;
    private String beta_decay_en;
    private String beta_decay_en_unc;
    private String qa,qa_unc;
    private String qec;
    private String qec_unc;
    private String sn;
    private String sn_unc;
    private String sp;
    private String sp_unc;
    private Double radii_val;
    private String radii_del;
    private String el_mom;
    private String mag_mom;
    private String abundance;
    private Double abundance_unc;
    private String ther_capture;
    private String ther_capture_unc;
    private String westcott_g;
    private String resonance_integ;
    private String resonance_integ_unc;
    private String tentative;

    @Override
    public String toString() {
        return new StringBuilder(70).append("Nuclide: ").append(nucid)
                .append("\nCharge number (Z):  ").append(z)
                .append("\nNumber of neutrons (N):  ").append(n)
                .append("\nHalf Life:  ").append(getFullHalfLife())
                .append("\nAtomic mass (A): ").append(getAtomicMass())
                .append("\nTotal angular momentum and parity (Jp): ").append(Objects.nonNull(jp) ? jp : "Unknown")
                .append("\n\n").toString();
    }

    // TODO: 05.09.2017 Убрать всё это из модели
    private String getFullHalfLife(){
        if ("STABLE".equals(half_life)){
            return "Stable";
        } else if(Objects.nonNull(half_life) && Helper.checkDouble(half_life) && Objects.nonNull(Helper.getUnit(half_life_unit))) {
            return half_life + " " + Helper.getUnit(half_life_unit);
        } else if(Objects.nonNull(half_life_sec) && half_life_sec > 0){
            return half_life_sec + " seconds";
        } else return "Unknown";
    }

    private String getAtomicMass(){
        return Objects.nonNull(atomic_mass) ? String.valueOf(Double.parseDouble(atomic_mass)/1000000) + " u" : "Unknown";
    }


}

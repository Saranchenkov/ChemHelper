package by.bsu.chemistry.isotopes;


import by.bsu.chemistry.util.Helper;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by Ivan on 03.09.2017.
 */
@Entity
@Table(name = "nuclides")
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

    public Isotope(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNucid() {
        return nucid;
    }

    public void setNucid(String nucid) {
        this.nucid = nucid;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Integer getL_seqno() {
        return l_seqno;
    }

    public void setL_seqno(Integer l_seqno) {
        this.l_seqno = l_seqno;
    }

    public String getJp() {
        return jp;
    }

    public void setJp(String jp) {
        this.jp = jp;
    }

    public String getHalf_life() {
        return half_life;
    }

    public void setHalf_life(String half_life) {
        this.half_life = half_life;
    }

    public String getHalf_life_unc() {
        return half_life_unc;
    }

    public void setHalf_life_unc(String half_life_unc) {
        this.half_life_unc = half_life_unc;
    }

    public String getHalf_life_unit() {
        return half_life_unit;
    }

    public void setHalf_life_unit(String half_life_unit) {
        this.half_life_unit = half_life_unit;
    }

    public Double getHalf_life_sec() {
        return half_life_sec;
    }

    public void setHalf_life_sec(Double half_life_sec) {
        this.half_life_sec = half_life_sec;
    }

    public String getMass_excess() {
        return mass_excess;
    }

    public void setMass_excess(String mass_excess) {
        this.mass_excess = mass_excess;
    }

    public String getMass_excess_unc() {
        return mass_excess_unc;
    }

    public void setMass_excess_unc(String mass_excess_unc) {
        this.mass_excess_unc = mass_excess_unc;
    }

    public String getBinding() {
        return binding;
    }

    public void setBinding(String binding) {
        this.binding = binding;
    }

    public String getBinding_unc() {
        return binding_unc;
    }

    public void setBinding_unc(String binding_unc) {
        this.binding_unc = binding_unc;
    }

    public String getAtomic_mass() {
        return atomic_mass;
    }

    public void setAtomic_mass(String atomic_mass) {
        this.atomic_mass = atomic_mass;
    }

    public String getAtomic_mass_unc() {
        return atomic_mass_unc;
    }

    public void setAtomic_mass_unc(String atomic_mass_unc) {
        this.atomic_mass_unc = atomic_mass_unc;
    }

    public String getBeta_decay_en() {
        return beta_decay_en;
    }

    public void setBeta_decay_en(String beta_decay_en) {
        this.beta_decay_en = beta_decay_en;
    }

    public String getBeta_decay_en_unc() {
        return beta_decay_en_unc;
    }

    public void setBeta_decay_en_unc(String beta_decay_en_unc) {
        this.beta_decay_en_unc = beta_decay_en_unc;
    }

    public String getQa() {
        return qa;
    }

    public void setQa(String qa) {
        this.qa = qa;
    }

    public String getQa_unc() {
        return qa_unc;
    }

    public void setQa_unc(String qa_unc) {
        this.qa_unc = qa_unc;
    }

    public String getQec() {
        return qec;
    }

    public void setQec(String qec) {
        this.qec = qec;
    }

    public String getQec_unc() {
        return qec_unc;
    }

    public void setQec_unc(String qec_unc) {
        this.qec_unc = qec_unc;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getSn_unc() {
        return sn_unc;
    }

    public void setSn_unc(String sn_unc) {
        this.sn_unc = sn_unc;
    }

    public String getSp() {
        return sp;
    }

    public void setSp(String sp) {
        this.sp = sp;
    }

    public String getSp_unc() {
        return sp_unc;
    }

    public void setSp_unc(String sp_unc) {
        this.sp_unc = sp_unc;
    }

    public Double getRadii_val() {
        return radii_val;
    }

    public void setRadii_val(Double radii_val) {
        this.radii_val = radii_val;
    }

    public String getRadii_del() {
        return radii_del;
    }

    public void setRadii_del(String radii_del) {
        this.radii_del = radii_del;
    }

    public String getEl_mom() {
        return el_mom;
    }

    public void setEl_mom(String el_mom) {
        this.el_mom = el_mom;
    }

    public String getMag_mom() {
        return mag_mom;
    }

    public void setMag_mom(String mag_mom) {
        this.mag_mom = mag_mom;
    }

    public String getAbundance() {
        return abundance;
    }

    public void setAbundance(String abundance) {
        this.abundance = abundance;
    }

    public Double getAbundance_unc() {
        return abundance_unc;
    }

    public void setAbundance_unc(Double abundance_unc) {
        this.abundance_unc = abundance_unc;
    }

    public String getTher_capture() {
        return ther_capture;
    }

    public void setTher_capture(String ther_capture) {
        this.ther_capture = ther_capture;
    }

    public String getTher_capture_unc() {
        return ther_capture_unc;
    }

    public void setTher_capture_unc(String ther_capture_unc) {
        this.ther_capture_unc = ther_capture_unc;
    }

    public String getWestcott_g() {
        return westcott_g;
    }

    public void setWestcott_g(String westcott_g) {
        this.westcott_g = westcott_g;
    }

    public String getResonance_integ() {
        return resonance_integ;
    }

    public void setResonance_integ(String resonance_integ) {
        this.resonance_integ = resonance_integ;
    }

    public String getResonance_integ_unc() {
        return resonance_integ_unc;
    }

    public void setResonance_integ_unc(String resonance_integ_unc) {
        this.resonance_integ_unc = resonance_integ_unc;
    }

    public String getTentative() {
        return tentative;
    }

    public void setTentative(String tentative) {
        this.tentative = tentative;
    }

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

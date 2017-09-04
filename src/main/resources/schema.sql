CREATE TABLE IF NOT EXISTS nuclides (
	id INT AUTO_INCREMENT,
	nucid	VARCHAR_IGNORECASE ( 10 ) NOT NULL,
	z	int ( 10 ) DEFAULT NULL,
	n	int ( 10 ) DEFAULT NULL,
	symbol	VARCHAR_IGNORECASE ( 45 ) DEFAULT NULL,
	l_seqno	int ( 10 ) DEFAULT NULL,
	jp	varchar ( 20 ) DEFAULT NULL,
	half_life	varchar ( 40 ) DEFAULT NULL,
	half_life_unc	varchar ( 10 ) DEFAULT NULL,
	half_life_unit	varchar ( 40 ) DEFAULT NULL,
	half_life_sec	double DEFAULT NULL,
	mass_excess	varchar ( 15 ) DEFAULT NULL,
	mass_excess_unc	varchar ( 10 ) DEFAULT NULL,
	binding	varchar ( 15 ) DEFAULT NULL,
	binding_unc	varchar ( 10 ) DEFAULT NULL,
	atomic_mass	varchar ( 15 ) DEFAULT NULL,
	atomic_mass_unc	varchar ( 10 ) DEFAULT NULL,
	beta_decay_en	varchar ( 15 ) DEFAULT NULL,
	beta_decay_en_unc	varchar ( 10 ) DEFAULT NULL,
	qa	varchar ( 15 ) DEFAULT NULL,
	qa_unc	varchar ( 10 ) DEFAULT NULL,
	qec	varchar ( 15 ) DEFAULT NULL,
	qec_unc	varchar ( 10 ) DEFAULT NULL,
	sn	varchar ( 15 ) DEFAULT NULL,
	sn_unc	varchar ( 10 ) DEFAULT NULL,
	sp varchar ( 15 ) DEFAULT NULL,
	sp_unc	varchar ( 10 ) DEFAULT NULL,
	radii_val	double DEFAULT NULL,
	radii_del	varchar ( 20 ) DEFAULT NULL,
	el_mom	varchar ( 20 ) DEFAULT NULL,
	mag_mom	varchar ( 20 ) DEFAULT NULL,
	abundance	varchar ( 20 ) DEFAULT NULL,
	abundance_unc	double DEFAULT NULL,
	ther_capture	varchar ( 15 ) DEFAULT NULL,
	ther_capture_unc	varchar ( 45 ) DEFAULT NULL,
	westcott_g	varchar ( 15 ) DEFAULT NULL,
	resonance_integ	varchar ( 15 ) DEFAULT NULL,
	resonance_integ_unc	varchar ( 45 ) DEFAULT NULL,
	tentative	varchar ( 1 ) DEFAULT NULL
);


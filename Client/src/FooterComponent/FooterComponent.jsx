import React from 'react';

import styles from './FooterComponent.module.css';



const FooterComponent = () => {
	return (
		<div className={styles.frameParent}>
			<div className={styles.frameGroup}>
				<div className={styles.frameContainer}>
					<img className={styles.frameChild} alt="" src="/Frame 3946.svg" />
					<div className={styles.div}>Знайди свій епічний лут серед кращих ігор!</div>
				</div>
				<div className={styles.frameDiv}>
					<div className={styles.frameContainer}>
						<div className={styles.div1}>Популярне</div>
						<div className={styles.group}>
							<div className={styles.div2}>Пригоди</div>
							<div className={styles.cookie}>Хоррор</div>
							<div className={styles.cookie}>Аркади</div>
						</div>
					</div>
					<div className={styles.frameContainer}>
						<div className={styles.div1}>Допомога</div>
						<div className={styles.group}>
							<div className={styles.cookie}>Політика конфіденційності</div>
							<div className={styles.cookie}>Публічний договір</div>
							<div className={styles.cookie}>
								<p className={styles.p}>Політика використання</p>
								<p className={styles.p}>{`файлів cookie `}</p>
							</div>
						</div>
					</div>
					<div className={styles.frameContainer}>
						<div className={styles.div1}>Зв’язатись з нами</div>
						<div className={styles.epicloot}>@2024EpicLoot</div>
						<div className={styles.parent3}>
							<img className={styles.icon} alt="" src="/instagramm.svg" />
							<img className={styles.icon1} alt="" src="/telegram.svg" />
							<img className={styles.icon2} alt="" src="/twitter.svg" />
						</div>
					</div>
				</div>
			</div>
		</div>);
};

export default FooterComponent;

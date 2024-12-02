import { FunctionComponent } from 'react';
import styles from './SystemRequirementsComponent.module.css';

const SystemRequirementsComponent = ({ product }) => {
  const renderRequirements = (title, data) => (
    <div className={styles.group}>
      <div className={styles.div1}>{title}:</div>
      <div className={styles.frameGroup}>
        <div className={styles.container}>
          <div className={styles.directx}>ОС:</div>
          <div className={styles.windows1064Bit}>{data.operatingSystem}</div>
        </div>
        <div className={styles.frameDiv}>
          <div className={styles.directx}>Процесор:</div>
          <div className={styles.windows1064Bit}>{data.processor}</div>
        </div>
        <div className={styles.parent1}>
          <div className={styles.directx}>Оперативна пам’ять:</div>
          <div className={styles.windows1064Bit}>{data.ramMemory}</div>
        </div>
        <div className={styles.parent2}>
          <div className={styles.directx}>Відеокарта:</div>
          <div className={styles.windows1064Bit}>
            {data.graphicCard.split(', ').map((line, index) => (
              <p key={index} className={styles.orIntelArc}>{line}</p>
            ))}
          </div>
        </div>
        <div className={styles.directxParent}>
          <div className={styles.directx}>DirectX:</div>
          <div className={styles.windows1064Bit}>{data.directX}</div>
        </div>
        <div className={styles.parent3}>
          <div className={styles.directx}>Місце на диску:</div>
          <div className={styles.windows1064Bit}>{data.diskSpace}</div>
        </div>

        <div className={styles.parent4}>
          <div className={styles.directx}>Додаткові вимоги:</div>
          <div className={styles.windows1064Bit}>{data.extra}</div>
        </div>
      </div>
    </div>
  );

  const { minimumRequirements, recommendedRequirements } = product;

  return (
    <div className={styles.parent}>
    <div className={styles.div}>Системні вимоги</div>
    <div className={styles.frameParent}>
      {renderRequirements('Мінімальні', minimumRequirements)}
      {renderRequirements('Рекомендовані', recommendedRequirements)}
    </div>
  </div>
  );
};

export default SystemRequirementsComponent;

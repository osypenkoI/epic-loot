package com.e_commerce.epic_loot.api.DTO.Product;

import lombok.Data;

@Data
public class RequirementsDTO {

    private Integer id; // ID требования

    private String operatingSystem; // Операционная система

    private String processor; // Процессор

    private String ramMemory; // Объем оперативной памяти

    private String graphicCard; // Видеокарта

    private String directX; // Версия DirectX

    private String diskSpace; // Необходимое дисковое пространство

    private String extra; // Дополнительные требования
}

package com.company.PresentationLayer;

import com.company.Entities.Agreement;
import com.company.LogicLayer.AgreementController;

public class AgreementsInterface {
    public static void createAgreement(Agreement agreement){
        AgreementController.AgreementCreator(agreement);
    }
    public static void addItemToAgreement(String providerId, String catalogItemId, int minAmount, double discount){
        AgreementController.addItem(providerId, catalogItemId, minAmount, discount);
    }

    public static void editItemAgreement(String providerId, String catalogItemId, Integer minAmount, Double discount){
        AgreementController.editItem(providerId, catalogItemId, minAmount, discount);
    }

    public static void removeItemFromAgreement(String providerId, String catalogItemId){
        AgreementController.removeItemByIds(providerId, catalogItemId);
    }
    public static String stringifyAgreementItems(String providerId){
        return AgreementController.stringifyAgreementItems(providerId);
    }
}

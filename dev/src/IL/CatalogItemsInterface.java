package IL;

import Entities.CatalogItem;
import BL.CatalogItemController;
import BL.CommunicationDetailsController;
import BL.ProviderController;

import java.util.List;

public class CatalogItemsInterface {

    public static void addItem(String providerId, CatalogItem catalogItem, String productDetailsId) {
        ProviderController.addCatalogItem(providerId,catalogItem,productDetailsId);
    }
    public static void editItem(String providerId, String catalogNum, double newPrice){
        ProviderController.editCatalogItem(providerId, catalogNum, newPrice);
    }
    public static void removeItem(String providerId, String catalogNum){
        ProviderController.removeCatalogItem(providerId,catalogNum);
    }
    public static List<CatalogItem> getAllItemsOfProvider(String providerId){
        return ProviderController.getAllProviderItems(providerId);
    }
}

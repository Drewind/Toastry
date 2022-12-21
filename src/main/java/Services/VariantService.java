package Services;

import Entities.Product;
import Entities.ProductVariant;
import Entities.ProductVariantOption;
import Models.ProductModel;
import Models.ProductVariantModel;
import Models.ProductVariantOptionModel;

import java.util.List;

public class VariantService {
    private ProductModel productModel;
    private ProductVariantModel variantModel;
    private ProductVariantOptionModel variantOptionModel;

    public void init(ProductModel productModel, ProductVariantModel variantModel, ProductVariantOptionModel optionModel) {
        this.productModel = productModel;
        this.variantModel = variantModel;
        this.variantOptionModel = optionModel;
    }

    public List<ProductVariant> getVariantsTiedToProduct(final List<String> variantIDs) {
        return variantIDs.stream().filter(this.variantModel::entityExists).map(this.variantModel::retrieve).toList();
    }

    public ProductVariantOptionModel variantOptionModel() { return this.variantOptionModel; }

    public ProductVariantModel variantModel() { return this.variantModel; }

    public boolean validateVariantsTiedToProduct(final Product product) {
        return product.getVariants().stream().allMatch(variantModel::entityExists);
    }

    /**
     * Validates each variant option tied to the variant in question exists in the program; otherwise, return false.
     * @param variant {@code Product} product.
     * @return {@code boolean} true if each variant is still valid and false otherwise.
     */
    public boolean validateVariantOptionsTiedToVariant(final ProductVariant variant) {
        return variant.getSelectionOptions().stream().allMatch(variantOptionModel::entityExists);
    }

    /**
     * Used to remove a given {@code ProductVariant} from ALL products that its tied too. Most commonly used to
     * delete a variant completely from the system.
     *
     * <br><br>
     * Works by invoking each product's {@code removeVariant} method which will return true if that product was
     * successfully deleted. Any product who removed the variant will then continue in the stream and have their
     * changes persisted through {@code ::upsert}.
     * @param variantToBeRemoved variant to remove from products
     */
    public void deleteVariantFromProducts(final ProductVariant variantToBeRemoved) {
        productModel.getEntities().stream()
                .filter(product -> product.removeVariant(variantToBeRemoved))
                .forEach(productModel::upsert);
    }

    /**
     * Removes ALL {@code ProductVariantOption}s tied to a given variant. Most commonly used when deleting a
     * {@code ProductVariant} from the system. Since variant options have a one-to-many relationship to their
     * parent variant, removing a variant requires deleting its variant options.
     * @param variantToBeRemoved to be removed
     */
    public void deleteVariantOptionsFromVariant(final ProductVariant variantToBeRemoved) {
        variantToBeRemoved.getSelectionOptions().stream()
                .filter(variantOptionModel::entityExists)
                .forEach(variantOptionModel::delete);
    }

    public void linkVariantOptionToVariant(final ProductVariant variant, final ProductVariantOption variantOption) {
        variantOptionModel.upsert(variantOption);
        variant.addSelectionOption(variantOption);
        variantModel.upsert(variant);
    }
}

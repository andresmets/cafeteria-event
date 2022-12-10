<!DOCTYPE html>
<html lang="et">
    <head>
        <title>Cafeteria event</title>
        <script type="text/javascript" src="/js/jquery-3.6.1.js"></script>
        <script type="text/javascript" src="/js/jquery-simple-template.js"></script>
        <script type="text/javascript" src="/js/products.js"></script>
        <script type="text/javascript" src="/js/validator.js"></script>
        <link rel="stylesheet" href="css/style.css"></link>
    </head>
    <body>
        <div style="display:none">
            <script type="text/html" id="item-category">
                <div class="row">
                    <h2 data-content="label" data-template-bind='[{"attribute": "data-type", "value": "type"}]'></h2>
                </div>
            </script>
            <script type="text/html" id="item-template">
                <div class="shopping-item" data-template-bind='[{"attribute": "data-item-id", "value": "id"}]'>
                    <div class="counts" data-template-bind='[{"attribute": "data-item-count", "value": "quantity"}]'></div>
                    <img class="item-name" data-template-bind='[{"attribute": "src", "value": "imageUrl"}, {"attribute": "data-item-id", "value": "id"}]'></img>
                    <div class="basket">
                        <div class="label" data-content="labelInBasket"></div>
                        <div class="selected">0</div>
                        <div class="icon delete"><img class="delete" src="/icons/delete.png" width="30"/></div>
                    </div>

                </div>
            </script>
            <script type="text/html" id="item-reserved-modal">
                <div class="item-reserved-modal">
                    <div class="msg" data-content="labelReserved"></div>
                </div>
            </script>
           <script type="text/html" id="shopping-basket-totals">
                <div class="checkout-info">
                    <div class="basket-totals">
                        <table>
                            <tr>
                                <td class="input-label" data-content="labelTotal"></td>
                                <td class="input-field" data-content="amount"></td>
                            </tr>
                        </table>
                    </div>
                    <div class="buttons">
                        <button class="checkout" data-content="labelCheckout"></button>
                        <button class="cancel" data-content="labelCancel"></button>
                    </div>
                </div>
           </script>
           <script type="text/html" id="shopping-basket-modal">
                <div class="overlay transparent" id="shopping-basket">
                    <div class="modal">
                        <div>
                            <table>
                                <tr>
                                    <td class="input-label" data-content="labelTotal"></td>
                                    <td class="input-field amount" data-content="amount"></td>
                                </tr>
                                <tr>
                                    <td class="input-label" data-content="labelCashReceived"></td>
                                    <td class="input-field"><input class="received" type="text" name="cashReceived"/></td>
                                </tr>
                                <tr>
                                    <td class="input-label" data-content="labelCashReturned"></td>
                                    <td class="input-field returned"></td>
                                </tr>
                            </table>
                        </div>
                        <div class="buttons clear">
                            <button class="checkout" data-content="labelCheckout"></button>
                            <button class="cancel" data-content="labelCancel"></button>
                        </div>
                    </div>
                </div>
           </script>
           <script type="text/html" id="shopping-basket-error">
                <div class="error" data-content="labelCashNotReceived"></div>
           </script>
           <script type="text/html" id="error-modal">
            <div class="overlay transparent" id="error-overlay">
                <div class="modal">
                    <div class="error" data-content="errorLabel"></div>
                    <div class="buttons clear">
                        <button class="cancel" data-content="labelClose"></button>
                    </div>
                </div>
            </div>
           </script>
        </div>
        <div class="types" style="display:none"></div>
    </body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>Liste des lots de paddy</title>
</head>
<body>

    <table width="80%">
        <tr>
            <td align="center">
                <fieldset style="width:300px;height:100px;">
                    <legend>Statistique</legend>

                    <br><br>
                        Quantité de lot de paddy transformé
                    <br><br>

                    <b>3</b>

                </fieldset>
            </td>

            <td align="center">
                <fieldset style="width:300px;height:100px;">
                    <legend>Statistique</legend>

                    <br><br>
                    Dépense total pour la transformation
                    <br><br>

                    <b>1 300 000 Ar</b> 

                </fieldset>
            </td>
        </tr>
    </table>

    <br><br>

    <table border="1" cellpadding="10" cellspacing="0" width="80%">
        <tr>
            <th>Référence</th>
            <th>Date</th>
            <th>Quantité</th>
            <th>Prix unitaire</th>
            <th>Total</th>
            <th>Action</th>
        </tr>

        <tr>
            <td>LPT001</td>
            <td>14/06/26</td>
            <td>500</td>
            <td>1000</td>
            <td>500000</td>
            <td>
                <form action="" method="get">
                    <input type="hidden" name="reference" value="LPT001">
                    <input type="submit" value="Voir détail">
                </form>
            </td>
        </tr>

        <tr>
            <td>LPT002</td>
            <td>15/06/26</td>
            <td>400</td>
            <td>1000</td>
            <td>400000</td>
            <td>
                <form action="" method="get">
                    <input type="hidden" name="reference" value="LPT002">
                    <input type="submit" value="Voir détail">
                </form>
            </td>
        </tr>

        <tr>
            <td>LPT003</td>
            <td>30/08/26</td>
            <td>200</td>
            <td>2000</td>
            <td>400000</td>
            <td>
                <form action="" method="get">
                    <input type="hidden" name="reference" value="LPT003">
                    <input type="submit" value="Voir détail">
                </form>
            </td>
        </tr>

    </table>


</body>
</html>
import java.util.Random;

public class Model
{
    public int MaxSize = 50;

    public int Size;
    private Random rand = new Random();
    private int[][] matrix;
    private int[][] foundVal = new int[2][3];

    public void GenerateMatrix(int size, int minVal, int maxVal)
    {
        Size = Math.min(size, MaxSize);
        matrix = new int[Size][Size];

        for(int i = 0; i < Size; ++i) {
            for(int j = 0; j < Size; ++j) {
                matrix[i][j] = rand.nextInt(maxVal-minVal)+minVal;
            }
        }
    }

    public void FindValues ()
    {
        foundVal = new int[2][3];
        boolean firstEncounter = true;

        for(int i = 0; i < Size; ++i) {
            for(int j = 0; j < Size; ++j) {
                if (i > j) {
                    if (firstEncounter) {
                        foundVal[0][0] = matrix[i][j];
                        foundVal[0][1] = i;
                        foundVal[0][2] = j;
                        firstEncounter = false;
                    } else if (matrix[i][j] <= foundVal[0][0]) {
                        foundVal[0][0] = matrix[i][j];
                        foundVal[0][1] = i;
                        foundVal[0][2] = j;
                    }
                }

                if (i + j < Size - 1 && matrix[i][j] >= foundVal[1][0]) {
                    foundVal[1][0] = matrix[i][j];
                    foundVal[1][1] = i;
                    foundVal[1][2] = j;
                }
            }
        }
    }

    public MatrixNode[][] DrawMatrix(boolean showResult)
    {
        MatrixNode[][] result = new MatrixNode[Size][Size];

        for(int i = 0; i < Size; i++) {
            for(int j = 0; j < Size; j++)
            {
                result[i][j] = new MatrixNode();
                result[i][j].value = matrix[i][j];

                if (i == j && i + j == Size - 1)
                {
                    result[i][j].elementType = MatrixTypes.midNode;
                }
                else if (i == j)
                {
                    result[i][j].elementType = MatrixTypes.mainDiagonal;
                }
                else if (i + j == Size - 1)
                {
                    result[i][j].elementType = MatrixTypes.sideDiagonal;
                }
                if(showResult)
                {
                    if (i == foundVal[0][1] && j == foundVal[0][2])
                    {
                        result[i][j].elementType = MatrixTypes.minValue;
                    }
                    else if (i == foundVal[1][1] && j == foundVal[1][2])
                    {
                        result[i][j].elementType = MatrixTypes.maxValue;
                    }
                }
            }
        }
        return result;
    }

    public String[] GetMatrixResults()
    {
        String[] result = new String[2];
        result[0] = ("min[" + (foundVal[0][2] + 1) + "][" + (foundVal[0][1] + 1) + "] = " + foundVal[0][0]);
        result[1] = ("max[" + (foundVal[1][2] + 1) + "][" + (foundVal[1][1] + 1) + "] = " + foundVal[1][0]);
        return result;
    }

    public void SwitchValues()
    {
        if(matrix!=null)
        {
            matrix[foundVal[0][1]][foundVal[0][2]] = foundVal[1][0];
            matrix[foundVal[1][1]][foundVal[1][2]] = foundVal[0][0];
            int temp = foundVal[0][1];
            foundVal[0][1] = foundVal[1][1];
            foundVal[1][1] = temp;
            temp = foundVal[0][2];
            foundVal[0][2] = foundVal[1][2];
            foundVal[1][2] = temp;
        }
    }

    public String ToHexColor(int r, int g, int b)
    {
        return String.format("#%02x%02x%02x", r, g, b);
    }
}


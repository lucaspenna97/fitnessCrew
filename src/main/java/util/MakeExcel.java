package util;

import bean.User;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class MakeExcel {

    private static String PATH = "C:/fitnessCrew";

    public MakeExcel () { }

    public static boolean generateFile (List<User> users) {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet usersSheet = workbook.createSheet("Alunos");

        int rowCount = 0;
        int cellCount = 0;

        //Head
        Row cabecalho = usersSheet.createRow(rowCount++);

        Cell cellCpfCabecalho = cabecalho.createCell(cellCount++);
        cellCpfCabecalho.setCellValue("CPF");
        cellCpfCabecalho.setCellStyle(formatCells(workbook, IndexedColors.AQUA.getIndex()));

        Cell cellNomeCabecalho = cabecalho.createCell(cellCount++);
        cellNomeCabecalho.setCellValue("Nome");
        cellNomeCabecalho.setCellStyle(formatCells(workbook, IndexedColors.BLUE.getIndex()));

        Cell cellIdadeCabecalho = cabecalho.createCell(cellCount++);
        cellIdadeCabecalho.setCellValue("Idade");
        cellIdadeCabecalho.setCellStyle(formatCells(workbook, IndexedColors.GREEN.getIndex()));

        Cell cellSexoCabecalho = cabecalho.createCell(cellCount++);
        cellSexoCabecalho.setCellValue("Sexo");
        cellSexoCabecalho.setCellStyle(formatCells(workbook, IndexedColors.GOLD.getIndex()));

        Cell cellAlturaCabecalho = cabecalho.createCell(cellCount++);
        cellAlturaCabecalho.setCellValue("Altura");
        cellAlturaCabecalho.setCellStyle(formatCells(workbook, IndexedColors.ORANGE.getIndex()));

        Cell cellPesoCabecalho = cabecalho.createCell(cellCount++);
        cellPesoCabecalho.setCellValue("Peso");
        cellPesoCabecalho.setCellStyle(formatCells(workbook, IndexedColors.RED.getIndex()));

        Cell cellEmailCabecalho = cabecalho.createCell(cellCount++);
        cellEmailCabecalho.setCellValue("Email");
        cellEmailCabecalho.setCellStyle(formatCells(workbook, IndexedColors.TURQUOISE.getIndex()));


        //Body
        for (User user: users) {

            Row row = usersSheet.createRow(rowCount++);
            cellCount = 0;

            Cell cellCpf = row.createCell(cellCount++);
            cellCpf.setCellValue(user.getCpf());

            Cell cellNome = row.createCell(cellCount++);
            cellNome.setCellValue(user.getNome());

            Cell cellIdade = row.createCell(cellCount++);
            cellIdade.setCellValue(user.getIdade());

            Cell cellSexo = row.createCell(cellCount++);
            cellSexo.setCellValue(user.getSexo());

            Cell cellAltura = row.createCell(cellCount++);
            cellAltura.setCellValue(user.getAltura());

            Cell cellPeso = row.createCell(cellCount++);
            cellPeso.setCellValue(user.getPeso());

            Cell cellEmail = row.createCell(cellCount++);
            cellEmail.setCellValue(user.getEmail());

        }

        try {

            boolean directoryCreated = new File(PATH).mkdirs();
            if (directoryCreated) System.out.println("Diretório " + PATH + " criado com sucesso.");

            FileOutputStream fileOutputStream = new FileOutputStream(new File(PATH + "/RelatórioAlunos.xlsx"));
            workbook.write(fileOutputStream);
            fileOutputStream.close();
            return true;

        } catch (FileNotFoundException e) {

            e.printStackTrace();
            System.err.println("Arquivo não encontrado: " + e.getMessage());
            return false;

        } catch (IOException e) {
            System.err.println("Erro ao editar arquivo: " + e.getMessage());
            return false;
        }

    }


    private static CellStyle formatCells (Workbook workbook, Short color) {

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setFillBackgroundColor(color);

        return cellStyle;
    }

}

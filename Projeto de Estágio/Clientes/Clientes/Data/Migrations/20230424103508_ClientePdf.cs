using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace Clientes.Data.Migrations
{
    public partial class ClientePdf : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "ClientePdfID",
                table: "PdfFiles",
                type: "int",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.CreateIndex(
                name: "IX_PdfFiles_ClientePdfID",
                table: "PdfFiles",
                column: "ClientePdfID");

            migrationBuilder.AddForeignKey(
                name: "FK_PdfFiles_Clientes_ClientePdfID",
                table: "PdfFiles",
                column: "ClientePdfID",
                principalTable: "Clientes",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_PdfFiles_Clientes_ClientePdfID",
                table: "PdfFiles");

            migrationBuilder.DropIndex(
                name: "IX_PdfFiles_ClientePdfID",
                table: "PdfFiles");

            migrationBuilder.DropColumn(
                name: "ClientePdfID",
                table: "PdfFiles");
        }
    }
}

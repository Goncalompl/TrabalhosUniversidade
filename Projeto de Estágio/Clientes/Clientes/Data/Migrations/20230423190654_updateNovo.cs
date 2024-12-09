using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace Clientes.Data.Migrations
{
    public partial class updateNovo : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Campanhas_Clientes_ClienteCamID",
                table: "Campanhas");

            migrationBuilder.DropIndex(
                name: "IX_Campanhas_ClienteCamID",
                table: "Campanhas");

            migrationBuilder.DropColumn(
                name: "ClienteCamID",
                table: "Campanhas");

            migrationBuilder.AddColumn<string>(
                name: "ClienteCampanha",
                table: "Campanhas",
                type: "nvarchar(max)",
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "ClienteCampanha",
                table: "Campanhas");

            migrationBuilder.AddColumn<int>(
                name: "ClienteCamID",
                table: "Campanhas",
                type: "int",
                nullable: true);

            migrationBuilder.CreateIndex(
                name: "IX_Campanhas_ClienteCamID",
                table: "Campanhas",
                column: "ClienteCamID");

            migrationBuilder.AddForeignKey(
                name: "FK_Campanhas_Clientes_ClienteCamID",
                table: "Campanhas",
                column: "ClienteCamID",
                principalTable: "Clientes",
                principalColumn: "Id");
        }
    }
}

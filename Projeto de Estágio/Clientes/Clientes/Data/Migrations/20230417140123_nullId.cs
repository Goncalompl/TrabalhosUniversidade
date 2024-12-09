using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace Clientes.Data.Migrations
{
    public partial class nullId : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Campanhas_Clientes_ClienteCamID",
                table: "Campanhas");

            migrationBuilder.AlterColumn<int>(
                name: "ClienteCamID",
                table: "Campanhas",
                type: "int",
                nullable: true,
                oldClrType: typeof(int),
                oldType: "int");

            migrationBuilder.AddForeignKey(
                name: "FK_Campanhas_Clientes_ClienteCamID",
                table: "Campanhas",
                column: "ClienteCamID",
                principalTable: "Clientes",
                principalColumn: "Id");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Campanhas_Clientes_ClienteCamID",
                table: "Campanhas");

            migrationBuilder.AlterColumn<int>(
                name: "ClienteCamID",
                table: "Campanhas",
                type: "int",
                nullable: false,
                defaultValue: 0,
                oldClrType: typeof(int),
                oldType: "int",
                oldNullable: true);

            migrationBuilder.AddForeignKey(
                name: "FK_Campanhas_Clientes_ClienteCamID",
                table: "Campanhas",
                column: "ClienteCamID",
                principalTable: "Clientes",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);
        }
    }
}

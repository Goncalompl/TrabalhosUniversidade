using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace Clientes.Data.Migrations
{
    public partial class lista_listaDigital : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<bool>(
                name: "ClienteAListaDigital",
                table: "Clientes",
                type: "bit",
                nullable: false,
                defaultValue: false);

            migrationBuilder.AddColumn<bool>(
                name: "ClienteAlista",
                table: "Clientes",
                type: "bit",
                nullable: false,
                defaultValue: false);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "ClienteAListaDigital",
                table: "Clientes");

            migrationBuilder.DropColumn(
                name: "ClienteAlista",
                table: "Clientes");
        }
    }
}

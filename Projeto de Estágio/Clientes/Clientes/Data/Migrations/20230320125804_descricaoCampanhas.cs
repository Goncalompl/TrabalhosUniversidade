using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace Clientes.Data.Migrations
{
    public partial class descricaoCampanhas : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "Descricao",
                table: "Campanha",
                type: "nvarchar(max)",
                nullable: false,
                defaultValue: "");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "Descricao",
                table: "Campanha");
        }
    }
}

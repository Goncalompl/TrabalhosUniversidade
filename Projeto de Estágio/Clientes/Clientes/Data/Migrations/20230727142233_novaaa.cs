using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace Clientes.Data.Migrations
{
    public partial class novaaa : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Agendamentos_Leads_LeadID",
                table: "Agendamentos");

            migrationBuilder.DropIndex(
                name: "IX_Agendamentos_LeadID",
                table: "Agendamentos");

            migrationBuilder.DropColumn(
                name: "LeadID",
                table: "Agendamentos");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "LeadID",
                table: "Agendamentos",
                type: "int",
                nullable: true);

            migrationBuilder.CreateIndex(
                name: "IX_Agendamentos_LeadID",
                table: "Agendamentos",
                column: "LeadID");

            migrationBuilder.AddForeignKey(
                name: "FK_Agendamentos_Leads_LeadID",
                table: "Agendamentos",
                column: "LeadID",
                principalTable: "Leads",
                principalColumn: "Id");
        }
    }
}

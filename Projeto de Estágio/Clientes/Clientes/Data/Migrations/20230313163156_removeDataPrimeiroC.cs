using System;
using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace Clientes.Data.Migrations
{
    public partial class removeDataPrimeiroC : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "DataPrimeroC",
                table: "Leads");

            migrationBuilder.DropColumn(
                name: "ClienteAListaDigital",
                table: "Clientes");

            migrationBuilder.DropColumn(
                name: "ClienteAlista",
                table: "Clientes");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<DateTime>(
                name: "DataPrimeroC",
                table: "Leads",
                type: "datetime2",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

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
    }
}

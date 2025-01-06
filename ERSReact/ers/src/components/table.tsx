import React from 'react';
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";

type Reimbursement = {
  id: number;
  status: 'PENDING' | 'APPROVED' | 'DENIED';
  description: string;
  amount: number;
}

interface TableComponentProps {
  data: Reimbursement[];
  showActions?: boolean;
  onApprove?: (id: number) => void;
  onDeny?: (id: number) => void;
}

export function TableComponent({
  data,
  showActions = false,
  onApprove,
  onDeny,
}: TableComponentProps) {
  const getStatusColor = (status: string) => {
    switch (status) {
      case 'PENDING':
        return 'bg-yellow-100 text-yellow-800';
      case 'APPROVED':
        return 'bg-green-100 text-green-800';
      case 'DENIED':
        return 'bg-red-100 text-red-800';
      default:
        return 'bg-gray-100 text-gray-800';
    }
  };

  return (
    <div className="rounded-md border">
      <Table>
        <TableHeader>
          <TableRow>
            <TableHead>Status</TableHead>
            <TableHead>Description</TableHead>
            <TableHead className="text-right">Amount</TableHead>
            {showActions && <TableHead>Actions</TableHead>}
          </TableRow>
        </TableHeader>
        <TableBody>
          {data.map((item) => (
            <TableRow key={item.id}>
              <TableCell>
                <Badge className={getStatusColor(item.status)}>
                  {item.status}
                </Badge>
              </TableCell>
              <TableCell>{item.description}</TableCell>
              <TableCell className="text-right">
                ${item.amount.toFixed(2)}
              </TableCell>
              {showActions && item.status === 'PENDING' && (
                <TableCell>
                  <div className="flex gap-2">
                    <Button
                      size="sm"
                      onClick={() => onApprove?.(item.id)}
                    >
                      Approve
                    </Button>
                    <Button
                      size="sm"
                      variant="destructive"
                      onClick={() => onDeny?.(item.id)}
                    >
                      Deny
                    </Button>
                  </div>
                </TableCell>
              )}
            </TableRow>
          ))}
          {data.length === 0 && (
            <TableRow>
              <TableCell colSpan={showActions ? 4 : 3} className="text-center text-muted-foreground">
                No reimbursements found
              </TableCell>
            </TableRow>
          )}
        </TableBody>
      </Table>
    </div>
  );
}